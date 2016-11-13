cocos学习笔记
================

- [Lua扩展导出](Lua扩展导出指南.md)

- [模拟器配置](模拟器配置.md)

- [Lua自动导出C++文件](Lua自动导出C++文件.md)

- `cocos2d-x 3.11`需要添加`Core Text`，否则有如下报错

``` objective-c
"_CTFramesetterCreateWithAttributedString", referenced from:

calculateShrinkedSizeForString(NSAttributedString**, objcobject*, CGSize, bool, int&) in libcocos2d iOS.a(CCDevice-ios.o)

"_CTFramesetterSuggestFrameSizeWithConstraints", referenced from:

calculateShrinkedSizeForString(NSAttributedString**, objcobject*, CGSize, bool, int&) in libcocos2d iOS.a(CCDevice-ios.o)

ld: symbol(s) not found for architecture arm64

```