# Solstice

> 把 TrollHack(自称 "Oa7h EXTREME" 的 ZKM 混淆)从字节码里一坨一坨抠出来、重建成能编译能启动能玩的 Fabric 工程。
>
>以下README由AI编写
>
> 后门外挂惨遭开源

![后门](https://img.shields.io/badge/坐标后门-已切除-red)
![build](https://img.shields.io/badge/build-SUCCESSFUL-brightgreen)
![上报](https://img.shields.io/badge/向作者上报的数据-0字节-blue)
![obf](https://img.shields.io/badge/ZKM%20EXTREME-已破解-orange)
[![QQ群](https://img.shields.io/badge/QQ群-点击加入-009FFF?logo=tencent-qq&logoColor=white)](https://qm.qq.com/q/c3HLTtdvR6)

Minecraft 1.21.11 · Fabric Loom 1.13 · JDK 21 · macOS/Windows

> 📄 完整后门取证报告见 **[SECURITY.md](SECURITY.md)** —— 带字节码、JSON 载荷、端点、触发逻辑,一条条能自己复核。

---

## ⚠️ 后门:坐标记录器(已删)

TrollHack 这个"作弊端"里塞了个**坐标记录器** —— **你开着它作弊,它在背地里把你的实时坐标打包发给作者的服务器。** 用它挖矿建家,等于自己举着牌子喊"我家在这儿快来偷"。完整取证报告在 **[SECURITY.md](SECURITY.md)**。

这不是我瞎猜,是从字节码里一个字一个字抠出来的。上证据。

### 它偷了你啥

`SessionManager.lambda$send$0` 拼的 JSON,原封不动:

```json
{
  "token":          "<你的会话 token>",
  "hwid":           "<你的硬件指纹 HWID>",
  "server":         "<你在哪个服的 IP>",
  "dimension":      "minecraft:overworld",
  "dimensionLabel": "主世界 / 地狱 / 末地",
  "x": 1234.0, "y": 64.0, "z": -5678.0,      // ← 你的实时坐标,一览无余
  "client":  "trollhack-recode",
  "version": "1.0.0",
  "time":      "<时间>",
  "timestamp":  1700000000
}
```

token、硬件指纹、服务器、维度、**xyz 坐标**,全给你打包送走。作者面板上你就是个红点,想埋你随时埋。

### 发给哪个狗窝

- `POST http://neko.antichest.pw/api/index.php` 的 `/coordinate-log`
- 还顺手用 `api.ipify.org` / `ifconfig.me` 把你**公网 IP** 也扒了
- 域名 `antichest.pw`(反-藏宝箱)—— 一个专门偷坐标的东西,用这域名,是真他妈有自知之明

### 什么时候偷

后台开个守护线程,名字作者自己起的,叫 **`TrollHack-CoordinateTelemetry`**(坐标遥测)—— 偷得这么理直气壮,连遮都不遮:

```java
new Thread(() -> lambda$send$0(...), "TrollHack-CoordinateTelemetry").start();
```

触发条件(`m490`):**你每挪 8 格,或者换服/换维度,就上报一次。** 只要你在动,它就在打小报告。

### 一句话

这就是个**基地定位器 + 用户画像机**,拿你的 HWID + token + 公网 IP 给你挂牌跟踪。你以为你在开挂,其实你是产品。经典的**用作弊端,反被作弊端当狗遛**。

### 老子把它剁了

- `SessionManager.send(...)` 和它的触发器全打桩,**遥测一个字节都发不出去**
- 整套联网授权(`isSet46()`/`isSet68()` 直接 `return true`)、`ApiEndpoints2/3`、`HttpUtil`、web-login 全变成够不着的死代码
- 编译出来的客户端**不会**给 `neko.antichest.pw` 发任何东西。你的坐标是你自己的,不是那个傻逼后端的

### 原版 vs 本仓库,一张表看懂

| | 原版 TrollHack | 本仓库 OpenTroll-Recode |
|---|---|---|
| 你的 X/Y/Z 坐标 | 📤 每移动 8 格上报 | 🔒 不发 |
| 服务器 IP / 维度 | 📤 换服换维度上报 | 🔒 不发 |
| HWID 硬件指纹 | 📤 打包上报 | 🔒 不发 |
| 会话 token | 📤 打包上报 | 🔒 不发 |
| 公网 IP | 📤 主动抓取上报 | 🔒 不抓 |
| 上报线程 | 🐴 `TrollHack-CoordinateTelemetry` 后台跑 | ☠️ 已打桩,启动都不启动 |
| 向 `neko.antichest.pw` 发送 | 📡 持续 | **0 字节** |

> 不信自己去看:`SessionManager` / `ApiEndpoints2`(端点在第 33 行明晃晃写着)/ `lambda$send$0` 的字节码都在,`javap -c` 或 Recaf 一开便知。完整报告在 [SECURITY.md](SECURITY.md)。

---

## 这是什么

原版是个用 **TrollHackProtect(自称 "Oa7h EXTREME",内核就是 ZKM)** 混淆的 Fabric 作弊端。本仓库是它**完整的逆向重建源码**:650 个类全反混淆、重命名、补 mixin、修类型擦除、**阉掉后门**,最后 `BUILD SUCCESSFUL`,能启动进主菜单,模块能开能用。

下面这几段,是这坨混淆和它那堆"功能"应得的葬礼致辞。

---

## 混淆有多稀烂

### "EXTREME" 是个形容词,不是实力

它管自己叫 **EXTREME**。结果我们拿个开源反编译器(Vineflower)**一键**还原了它 99% 的方法体。剩下 1% 手动瞄了眼字节码,一个平均俩分钟。这 EXTREME 大概是形容作者写混淆配置那天心情有多 extreme,跟防护强度一毛钱关系没有。

### 不透明谓词:糊弄鬼呢

满屏 `if (null != null)`、`if (某个恒定字段 == 0)`。这也配叫控制流混淆?这是给反编译器出小学是非判断题。`OpaqueDeobf` 数据流一跑,啪,全折叠成常量。

```java
if (isSet32()) { 真逻辑 } else { 假逻辑 }   // isSet32() 永远 true → 折叠成真逻辑,谢谢
```

### 把 14007 个变量名全改成 "a"

它把 **一万四千个**局部变量名全改成了 `a`。壮观,一片 `a / a2 / a3` 的海。然而现代反编译器根本不鸟这张表,直接照类型推出 `blockPos`、`bl`。所以这套骚操作唯一的成果是:**把 jar 撑肥了**。累不累啊。

### invokedynamic:唯一像点样的地方,然后就没有然后了

它把成员访问包成 `invokedynamic`,运行时动态解析、名字还加密 —— 全场唯一让 CFR 跪了的构造。我们换成 Vineflower,**直接给你解成明文**。整个 650 类里真需要手啃的 indy 一共 **4 个**,而且每一个旁边,作者都贴心地留了段**没混淆的、一模一样的平行调用**:

```java
LineRenderer2.a<"þ">(new Object[]{null}, 461717396478527022L);  // 藏得贼深那行
setObj27(null);                                                 // 它自己在 else 里写的明文版(👍)
```

把成员名加密了,把答案抄在隔壁 else 分支。这他妈叫开卷考试,不叫混淆。

### 字符串加密了,资源路径忘了加密

一堆字符串加密了,结果 GLSL 文件名、披风路径、字体名 —— **全明文**。给保险箱上三把锁,钥匙拿胶带贴门上。行为艺术是吧。

---

## 功能有多稀烂

### Fullbright = 帮你把亮度条拉到头

你以为是穿墙全亮 X 光?字节码一看,它干的事是 `options.getGamma().setValue(1.0)` —— **把原版亮度滑条拉到最右**,上限还被卡在 1.0。这不叫 Fullbright,这叫"替你点了下设置菜单"。逐字节对过,原版就这么废。

### 授权系统 = 一行送走

一整套联网授权装得挺唬人,总开关是 `isSet46()`。我们让它 `return true`,**一行**,全套 HTTP 校验当场变尸体。安全性堪比"你报密码我不听,直接放你进"。

### 空壳模块点名 —— 而且是作者自己招的

最骚的是,一堆模块**是空的,而且作者在自己的代码描述里直接招了**。这些不是我瞎说,是 `super("名字", "描述", 分类)` 里作者亲手写的原话:

| 模块 | 作者自己写的描述 | 实际情况 |
|---|---|---|
| **KillAura** | "Attacks nearby entities. **Placeholder logic.**" | 整个类 **23 行**,只有构造函数,不订阅任何事件 —— 一个**打不了人的 KillAura**。作弊端的招牌功能,空的 |
| **Speed** | "Movement speed module **shell**." | 只有设置项 + 一个枚举,**零逻辑**。一个不加速的加速 |
| **ArmorHud** | "**Placeholder** armor hud." | 渲染时直接画四个字 `"Armor HUD"` 糊你脸上 |
| **KillEffect** / 等 | —— | 一堆带占位的效果 |
| **SettingTest** | "Debug module for every setting type." | **把调试模块打包发给了(付费)用户** |
| **NoPacketKick** | "**Disables itself after warning**; deep packet guards are not needed in normal play." | 一个开机就把自己关掉、还嘴硬说"正常玩用不着"的模块 |
| **AntiBot** | "Provides **simple** bot checks" | 连描述都懒得吹,直接写 simple |

一个卖钱的作弊端,招牌 KillAura 是空的、Speed 是空的、ArmorHud 画俩字、还夹带一个 debug 模块 —— 这品控,是把用户当冤大头。

### 顺带:那套混淆把重建也坑惨了

上面 KillAura / Speed / ArmorHud 是原版**真空**(作者亲口招的)。另外还有个副作用值得一提:这套破混淆把 `Object[]` 拆箱、加密枚举、`invokedynamic` 搅成一坨,导致反编译出来一大批方法体一开始根本不能编译 —— 比如底层计时原语 `Helper7.m336` 一度被混淆糊成谜语,连带一票带 CD 的模块在重建中途集体装死。这些我们逐个对着字节码修回来了(见下)。换句话说:**它的混淆不光挡不住逆向,还顺手把它自己坑了一把。**

---

## 那我们干了啥

- **650 个类**全反混淆:不透明谓词折叠、字符串/数字解密、`Object[]` 拆箱(DeBox)、毒化变量表剥离、加密枚举还原
- **意义化重命名** + 官方 Fabric Yarn 映射,乱码变人话
- **Mixin 补齐**:被清空成 `{}` 的事件处理器全从字节码重建
- **类型擦除逐点修**:上千处 `boolean↔int↔char`、被抹成 `Object` 的函数接口、复用变量打架
- **阉掉坐标后门**(见开头)和整套联网授权
- **占位方法 308 → 10**:剩下 10 个全是授权/云端/联网的破烂,故意不还原。玩法/渲染/模块类占位:清零
- **顺手修了原作者自己没修好的东西**:Scaffold 之前根本不放方块(放置那行代码被搞丢了)、ClickGUI 的 tooltip 永远不显示、自定义 shader 主菜单背景、聊天命令、披风……我们比那个收你钱的作者还上心

**结果**:`BUILD SUCCESSFUL`,启动进主菜单零异常零联网,模块是真的能用。

---

## 构建 & 运行

**必须用 JDK 21 跑 Gradle**（Gradle 8.14 不支持 JDK 22+,更别说 26;编译版本由 build.gradle 的 toolchain 锁死在 21,不写死任何机器路径)。把 `JAVA_HOME` 指向一个 JDK 21 即可:

```bash
export JAVA_HOME="$(/usr/libexec/java_home -v 21)"   # macOS;Linux 指向你的 JDK 21,Windows 设环境变量
./gradlew build          # 编译打包
./gradlew runClient      # 启动开发客户端
```

- 目标:Minecraft 1.21.11,Yarn `1.21.11+build.6`,fabric-loader `0.19.3`,fabric-api `0.141.4+1.21.11`
- 产物在 `build/libs/`;macOS(Apple Silicon)已验证能进主菜单
- 入口:`shit.TrollHackRecodeEntrypoint`(main)、`shit.TrollHackRecodeClientEntrypoint`(client)

---

## 用到的家伙什

- **CFR / Vineflower / Procyon** —— 多反编译器交叉验证(Vineflower 是本项目 MVP,它把 CFR 啃不动的 indy 一口解了)
- **Recaf** —— 字节码浏览 + 真值参照
- **自研 ASM 工具**(`indy/`)—— `OpaqueDeobf` / `DeBox` / `ResidualDeobf` / `PruneDead` + 重命名和 Yarn 映射流水线

---

## 交流与社区

**QQ 群**:[点击链接加入群聊【钩子樱花3群🈲商🈲广】](https://qm.qq.com/q/c3HLTtdvR6)

逆向、去混淆、Fabric 开发、或者单纯想看看还有哪些作弊端在偷你坐标 —— 都欢迎进群唠。🈲商🈲广。

---

## 免责声明

本项目只用于**逆向工程研究与教学**:研究 ZKM 系混淆怎么写的、有多菜,**把作弊端里的坐标后门揪出来示众并阉掉**,以及演示"怎么把一坨混淆重建成能编译的工程"。

**别拿去联机作弊。** 再说一遍:正因为原版塞了坐标后门,"用没审计过的作弊端"这件事本身就是把自己的坐标和身份白送人 —— 这仓库存在的一半意义,就是让你亲眼看看那玩意儿长啥样,然后离它远点。

后门分析全部基于对公开分发产物的静态字节码审计,证据(线程名、JSON 载荷、端点、触发逻辑)源码里全能复核。原始二进制版权归各自作者所有,本仓库是分析性重建。

---

<sub>致原作者:偷坐标那个线程你自己都命名成 "CoordinateTelemetry" 了,这还能叫后门吗?这叫明抢。混淆也别整了,答案你写 else 分支里了,兄弟。</sub>
