
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
