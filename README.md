# Talismans-Ykn

适用于 Minecraft 1.21.10 及以上版本的 Talismans 定制版本，当前版本为 `1.1`。

主要内容：

- `/talismans admin` 护符管理与配方编辑 GUI。
- 218 个中文护符配置，包含原版、AuraSkills、空岛和服务器玩法联动效果。
- 护符稀有度与多阶段成长体系。
- 禁止玩家食用、放置或触发护符底层物品的右键用途。

## 构建

使用 JDK 25 与 Gradle 9.1：

```powershell
gradle -PserverRoot="F:/你的服务端目录" clean build
```

`serverRoot` 的 `plugins` 目录需包含你有权使用的 Talismans 基础 JAR，以及兼容版本的 `eco` 和 `libreforge`。构建结果位于：

```text
build/libs/Talismans-Ykn-1.1.jar
```

## 安装

关闭服务器，移走旧的 `Talismans*.jar`，将新 JAR 放入 `plugins` 后完整重启。目标服务器需具备对应的 AuraSkills 与其他玩法插件，才能启用相关联动效果。
