KCP协议理解
================
> 这里的解释是基于KCP [JAVA版本](https://github.com/hkspirt/kcp-java)
## 1. 基本概念

KCP是基于UDP实现的可靠传输协议。一个UDP连接分配一个会话，分配一个KCP，用于托管该UDP
发包管理。

### `Segment`概念

`Segment`是KCP中一个包的格式封装，包头部分24个长度，其余是数据部门。总体包的大小不超过一个`MTU`大小1440

包头的定义如下：
- `conv` 会话标识
- `cmd` 包的指令
    - `IKCP_CMD_PUSH` 发送数据
    - `IKCP_CMD_ACK` ACK包
    - `IKCP_CMD_WASK` 探查滑动窗口大小
    - `IKCP_CMD_WINS` 告知滑动窗口大小
- `frg` 分片标识
    
    `frg`为0是最后一个分包
- `wnd` 自己的接受滑动窗口大小

    `wnd` = 接受滑动窗口大小(`rcv_wnd`) - 已接受队列大小`nrcv_que.size()`

- `ts` 发送时间戳
- `sn` 包的编号，ack恢复的内容
- `una` ack的一种，标识这之前的包都已经收到
- `resendts` 预计开始传输时间
- `rto` 超时重传时间
- `fastack` 是否需要快速ack，在收到ack时候ack之前的包的fastack会被增加，fastack用于是否快速重传
- `xmit` 这个没明白起什么作用

## 2. 发包流程
发包的基本过程：

上层调用`Send`方法将数据交给KCP，KCP做了如下处理
- 根据mtu大小划分包的个数
- 生成segment放入`nsnd_que`发送队列

## 3. 接受包的流程
接受包的基本过程：

上层调用`Input`将接受到的数据交给KCP，KCP做如下处理：
- 解析包，解析成Segment
- 更新远端的滑动窗口，表示远端还可以继续接受包的个数
- 更新una
    - 将发送队列`nsnd_buf`中sn小于una的包全部删除
    - 更新自己的`snd_una`，默认就是发送缓冲队列中的第一个包的sn，如果发送缓冲队列为空，则为下一个要发送包的sn
- 根据cmd做不同处理
    - cmd = IKCP_CMD_ACK，ACK回复包
        - 根据ack调整rto
        ``` java
        if (0 == rx_srtt) {
            rx_srtt = rtt;
            rx_rttval = rtt / 2;
        } else {
            // 和上一次相比
            int delta = (int) (rtt - rx_srtt);
            if (delta > 0) {
                delta = -delta;
            }

            rx_rttval = (3 * rx_rttval + delta) / 4;
            rx_srtt = (7 * rx_srtt + rtt) / 8;
            if (rx_srtt < 1) {
                rx_srtt = 1;
            }
        }

        int rto = (int) (rx_srtt + KCPCoder._imax_(1, 4 * rx_rttval));
        rx_rto = KCPCoder._ibound_(rx_minrto, rto, IKCP_RTO_MAX);
        ```
        - 根据ack的sn，移除发送缓冲队列中的对应包，之前包的fastack++
    - 更新自己的`snd_una`
    - cmd == IKCP_CMD_PUSH  数据包
        - 生成一个ack放入`acklist`
        - 将包插入到合适的位置`nrcv_buf`
        - 将编号连续的包放入`nrcv_que`
    - cmd == IKCP_CMD_WASK  滑动窗口询问包
        - 标记prob为`probe`
    - cmd == IKCP_CMD_WINS 告知滑动窗口，不做特殊处理。任何一个包都会更新滑动窗口

## 4. flush流程

上层定时tick KCP，KCP调用flush

- 发送残留的ACK包
- 如果有`IKCP_ASK_SEND`标记，发送`IKCP_CMD_WASK`
- 如果有`IKCP_ASK_TELL`标记，放`IKCP_CMD_WINS`
- 计算可以发送包的个数 min(`snd_wnd`， `rmt_wnd`)
- `nsnd_que`中取指定个数包发送, 加入到`nsnd_buf`缓冲队列
- 遍历`nsnd_buf`
    - 新加入的包发送
    - 当前时间戳超过预期的超时重传时间，再次发送
    - fastack超过指定值, 配置了快速重传，再次发送
    - 调用底层发送包

