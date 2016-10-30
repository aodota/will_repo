Lua自动导出C++文件
================
> 本文以如何导出CCHttpRequest为例，说明自动导出的整个过程

## 一、环境搭建
按照`cocos2d-x\tools\tolua\README.mdown`搭建环境，这里拷贝一份[点击查看](Lua自动导出环境搭建.md)


## 二、建立ini文件

[下载地址](cocos2dx_httprequest.ini)

这个ini文件我是以`cocos2dx_spine.ini`为模板修改，主要修改点如下：
- `[cocos2dx_httprequest]`这个要和文件名匹配，否则后续运行脚本时候会提示`Section not found in config file`错误
- `prefix = cocos2dx_httprequest`这个建议和文件名保持一致，否则也可能导致提示`Section not found in config file`错误
- `target_namespace = cc`这个是你需要将你的包导入到的命名空间，建议使用`cc`，我们使用的是Cocos啊，哈哈
- `cocos_headers` 头文件查找地址，这个可以先只添加你需要导出的头文件地址，后续报错时候再添加依赖的头文件地址
- `headers = %(cocosdir)s/tools/simulator/libsimulator/lib/network/CCHTTPRequest.h` 需要导出的头文件
- 各项具体含义脚本中有解释，如不清楚可参考这篇文章[文章1][1]、[文章2][2]

## 三、修改`genbindings.py`

添加自己的ini，注释掉已经生成过的ini，修改导出路径
```Python
cmd_args = {
            # 'cocos2dx.ini' : ('cocos2d-x', 'lua_cocos2dx_auto'), \
            # 'cocos2dx_extension.ini' : ('cocos2dx_extension', 'lua_cocos2dx_extension_auto'), \
            # 'cocos2dx_ui.ini' : ('cocos2dx_ui', 'lua_cocos2dx_ui_auto'), \
            # 'cocos2dx_studio.ini' : ('cocos2dx_studio', 'lua_cocos2dx_studio_auto'), \
            # 'cocos2dx_spine.ini' : ('cocos2dx_spine', 'lua_cocos2dx_spine_auto'), \
            # 'cocos2dx_physics.ini' : ('cocos2dx_physics', 'lua_cocos2dx_physics_auto'), \
            # 'cocos2dx_experimental_video.ini' : ('cocos2dx_experimental_video', 'lua_cocos2dx_experimental_video_auto'), \
            # 'cocos2dx_experimental.ini' : ('cocos2dx_experimental', 'lua_cocos2dx_experimental_auto'), \
            # 'cocos2dx_controller.ini' : ('cocos2dx_controller', 'lua_cocos2dx_controller_auto'), \
            # 'cocos2dx_cocosbuilder.ini': ('cocos2dx_cocosbuilder', 'lua_cocos2dx_cocosbuilder_auto'), \
            # 'cocos2dx_cocosdenshion.ini': ('cocos2dx_cocosdenshion', 'lua_cocos2dx_cocosdenshion_auto'), \
            # 'cocos2dx_3d.ini': ('cocos2dx_3d', 'lua_cocos2dx_3d_auto'), \
            # 'cocos2dx_audioengine.ini': ('cocos2dx_audioengine', 'lua_cocos2dx_audioengine_auto'), \
            # 'cocos2dx_csloader.ini' : ('cocos2dx_csloader', 'lua_cocos2dx_csloader_auto'), \
            # 'cocos2dx_experimental_webview.ini' : ('cocos2dx_experimental_webview', 'lua_cocos2dx_experimental_webview_auto'), \
            # 'cocos2dx_physics3d.ini' : ('cocos2dx_physics3d', 'lua_cocos2dx_physics3d_auto'), \
            # 'cocos2dx_navmesh.ini' : ('cocos2dx_navmesh', 'lua_cocos2dx_navmesh_auto'), \
            'cocos2dx_httprequest.ini' : ('cocos2dx_httprequest', 'lua_cocos2dx_httprequest_auto'),\
            }
```

`cmd_args`参数解释：
- 第一个是文件名
- 第二个是文件中的块即`[cocos2dx_httprequest]`定义
- 第三个是导出的文件名

修改导出路径，方便得到导出文件，建议修改到脚本所在目录，我的修改如下：

`output_dir = '%s/tools/tolua/auto' % project_root`

接下来运行脚本，根据错误提示修改，直到成功导出

## 四、常见错误以及解决方法

1. `C:\Program`未知命令
> 解决方法：<br>
> 1. Python直接安装到不带空格的目录。 <br>
> 2. 使用`progra~1`代替`Program Files`

2. `"'winsock2.h' file not found"`某个头文件找不到
> 解决办法：<br>
> 1. 首先将需要导出的头文件中对相关文件的引用去除，我导出`CCHttpRequest`就去除了'curl.h'的引用，并且修改头文件使之编译通过。这里掌握的原则就是不影响导出内容即可。<br>
> 2. 找到缺失的.h文件，添加到`cocos_headers`里<br>
> 下面是我对`CCHttpRequest.h`的修改：

	```cpp
	//#include "curl/curl.h"

	// 省略内容
	....

	//CURL *_curl;
	void *_formPost;
	void *_lastPost;
	```
3. `The namespace (cocos2d::extra::HTTPRequest) conversion wasn't set in 'ns_map' section of the conversions.yaml`命名空间错误
> 解决办法：
> 1. 修改`conversions.yaml`，位置在`tools\bindings-generator\targets\lua`中的`ns_map`添加自己的命名空间，我的修改如下：

	```yaml
	ns_map:
		"cocos2d::experimental::ui::": "ccexp."
		"cocos2d::experimental::": "ccexp."
		"cocos2d::extension::": "cc."
		"cocos2d::ui::": "ccui."
		"cocos2d::": "cc."
		"spine::": "sp."
		"cocostudio::timeline::": "ccs."
		"cocostudio::": "ccs."
		"cocosbuilder::": "cc."
		"CocosDenshion::": "cc."
		"cocos2d::tweenfunc::": "cc."
		# 自定义
		"cocos2d::extra::": "cc."
	```
4. 解决传入LuaFunction的问题
> 解决办法：
> 1. `LUA_FUNCTION`自动导出时会当作`int`处理，这样导出的类无法传入Lua Function. 这时需要手动修改导出类，我的修改如下：

	```cpp
	// 将是LUA_FUNCTION的参数按照这种方式处理即可解决问题
	LUA_FUNCTION arg0;
	#if COCOS2D_DEBUG >= 1
			if (!toluafix_isfunction(tolua_S, 2, "LUA_FUNCTION", 0, &tolua_err))
			{
				goto tolua_lerror;
			}
	#endif
			arg0 = (toluafix_ref_function(tolua_S, 2, 0));
	```


## 五、添加到LuaStack，供Lua使用

我是放在项目的`Classes`的`auto`文件夹下，修改`AppDelegate.cpp`引入导出的类，代码如下：

```cpp
#include "auto/lua_cocos2dx_httprequest_auto.hpp"

// 省略其他代码
...

// register custom function
static int register_constom_lua(lua_State* L)
{
    register_all_cocos2dx_httprequest(L);
	return 0;
}
```

## 六、修改CCHTTPRequest.h避免链接错误
```cpp
#ifndef CC_LUA_ENGINE_ENABLED
#define CC_LUA_ENGINE_ENABLED 1
#endif // !CC_LUA_ENGINE_ENABLED

#if CC_LUA_ENGINE_ENABLED > 0
#include "CCLuaEngine.h"
#endif
```

编译项目，成功后Lua就可以使用啦~~

[1]: http://www.cocoachina.com/bbs/read.php?tid=196416
[2]: http://www.cocoachina.com/bbs/read.php?tid-226362-page-1.html

