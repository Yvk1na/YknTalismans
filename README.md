# YknTalismans

适用于 Minecraft 1.21.10 及以上版本的 Talismans 定制版本，当前版本为 `1.1`。

主要内容：

- `/ykntalismans admin` 护符管理与配方编辑 GUI。
- 护符袋默认 45 格、每格一个护符，支持多页浏览和手持护符右键快速存入。
- 管理员可用 `/ykntalismans plus <数值>` 与 `reduce <数值>` 调整自己的护符袋容量。
- 214 个中文护符配置，包含原版、AuraSkills、空岛和服务器玩法联动效果。
- 护符稀有度与多阶段成长体系。
- 禁止玩家将护符用于酿造、熔炼、装备、战斗、交互等非护符用途。
- 保留 `talismans:*` 内部命名空间，兼容已有护符、护符袋和升级配方。

## 构建

使用 JDK 25 与 Gradle 9.1：

```powershell
gradle -PserverRoot="F:/你的服务端目录" clean build
```

`serverRoot` 的 `plugins` 目录需包含你有权使用的 Talismans 基础 JAR，以及兼容版本的 `eco` 和 `libreforge`。构建结果位于：

```text
build/libs/YknTalismans-1.1.jar
```

## 安装

关闭服务器，移走旧的 `Talismans*.jar`，将新 JAR 放入 `plugins` 后完整重启。升级已有服务器时，请把旧数据目录 `plugins/Talismans` 复制为 `plugins/YknTalismans`。目标服务器需具备对应的 AuraSkills 与其他玩法插件，才能启用相关联动效果。
