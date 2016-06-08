Lua扩展导出指南
================

## 1. `lpack`支持

### 下载&安装
- [下载地址](http://webserver2.tecgraf.puc-rio.br/~lhf/ftp/lua/#lpack)

### 问题解决
> `void *` 不能自动转换成`char *`

显示添加强制转换

> 链接错误

因为是C++项目，EXE引用的lua头文件必须这样写

```C
extern "C" {
#include "lua.h"
#include "lualib.h"
#include "lauxlib.h"
}
```
> 重复定义

建立`lpack.h`，内容如下：

```C
#ifndef __LUA_LPACK_H_
#define __LUA_LPACK_H_

extern "C" {
#include "lua.h"
#include "lualib.h"
#include "lauxlib.h"
}

int luaopen_pack(lua_State *L);

#endif
```

### 导出到Lua

1) 建立`lua_export.h`

```C
#pragma once
#ifndef __LUA_EXPORT_H_
#define __LUA_EXPORT_H_


extern "C" {
#include "lauxlib.h"
}

void luaopen_lua_exts(lua_State *L);


#endif
```

2) 建立`lua_export.c`

```C
#include "lua_export.h"
#include "lpack/lpack.h"

static luaL_Reg lua_exts[] = {
	{ "pack", luaopen_pack},
	{ NULL, NULL }
};

void luaopen_lua_exts(lua_State *L)
{
	// load extensions
	luaL_Reg* lib = lua_exts;
	lua_getglobal(L, "package");
	lua_getfield(L, -1, "preload");
	for (; lib->func; lib++)
	{
		lua_pushcfunction(L, lib->func);
		lua_setfield(L, -2, lib->name);
	}
	lua_pop(L, 2);
}

```

## 2. `zlib`支持
### 下载&安装
- [下载地址](http://zlib.net/)

### 问题解决
1) 建立`lua_zlib.h`文件

```C
#ifndef LUA_ZLIB_H__1
#define LUA_ZLIB_H__1
/*=========================================================================*\
* LuaSocket toolkit
* Networking support for the Lua language
* Diego Nehab
* 9/11/1999
\*=========================================================================*/
extern "C" {
#include "lua.h"
#include "lauxlib.h" // 很重要
}

/*-------------------------------------------------------------------------*\
* Current socket library version
\*-------------------------------------------------------------------------*/


/*-------------------------------------------------------------------------*\
* This macro prefixes all exported API functions
\*-------------------------------------------------------------------------*/

/*-------------------------------------------------------------------------*\
* Initializes the library.
\*-------------------------------------------------------------------------*/
LUALIB_API int luaopen_zlib(lua_State *L);
#endif /* LUA_ZLIB_H */

```

2) `lua_zlib.c`头部修改如下

```C
#include <ctype.h>
#include <stdlib.h>
#include <string.h>
#include <zlib.h>
#include "lua_zlib.h"
```

3) 添加导出

在`lua_export.c`中添加

```C
#include "lua_zlib/lua_zlib.h"

static luaL_Reg lua_exts[] = {
	{ "pack", luaopen_pack},
	{ "zlib", luaopen_zlib},
	{ NULL, NULL }
};
```

## 3. `cjson`支持

### 下载&安装
- [下载地址](http://www.kyne.com.au/~mark/software/lua-cjson.php)

1) 建立`lua_cjson.h`

```C
#ifndef __LUA_CJSON_H_
#define __LUA_CJSON_H_

extern "C" {
#include "lua.h"
#include "lualib.h"
#include "lauxlib.h"
#include "strbuf.h"
#include "fpconv.h"
}

#if defined(_MSC_VER)
#define strncasecmp _strnicmp
#endif

int luaopen_cjson(lua_State *l);
int luaopen_cjson_safe(lua_State *l);

#endif
```

**注意：**其他步骤和上面2个类似

## 总结
1) vs2015时导入c的头文件时候一定要用如下方式导入
```C
extern "C" {
    // 要导入的头文件
    ...
}
```

2) 直接引用c时会出现重复定义的错误提示。需要建立一个.h文件，通过引用头文件解决问题

3) 对于一些C代码在`win32`平台可能会出现编译错误，按照错误提示修改即可