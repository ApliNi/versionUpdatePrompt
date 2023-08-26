# versionUpdatePrompt
如果客户端版本低于服务器版本, 则发出提示.

下载: https://modrinth.com/plugin/versionupdateprompt/

```yaml
# 消息
message:
  # %playerVersion% - 客户端版本
  # %serverVersion% - 服务器版本

  # 如果玩家的版本低于服务器版本
  lower: '§6IpacEL §f> §b新的 Minecraft§a[%serverVersion%] §b版本可用!'
  # 如果版本相等
  equal: ''
  # 如果玩家的版本高于服务器版本
  higher: ''


# 服务器配置
server:
  # 连接到 AuthMe, 等待玩家登录成功后再运行
  hook_AuthMe: false
  # 玩家加入服务器后等待多长时间再检查版本 (毫秒
  delay: 4000

  # 默认自动检查版本
  serverVer: -1
  serverVerName: ''

```