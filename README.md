使用Java开发命令行工具？这听起来确实有点不可思议，因为在我们认知中，Java的启动速度慢，而命令行工具的使用是一次性的，不像web服务那样可以进行预热。所以除了命令本身执行的时间，还要算上JVM启动的时间，这确实不是一个好主意。

但有了Graalvm之后，可以对Java程序进行native编译，这时，使用Java开发命令行工具似乎也没有那么糟糕，不仅没有了JVM启动慢的问题，也带来了一定的性能提升，而且Java本身的性能也不差，于是乎我就开始了我的开发之旅。编译产物的大小确实令人不满。

项目中我尽量避免使用反射和第三方依赖，我也追求清晰合理的代码设计，不过受限于个人能力，项目中可能存在一些不合理的设计，也有不少地方需要改进。

当前的功能较少，我的想法是将本项目扩展为一个涵盖各种采用命令行工具的工具包，后期将视情况新增功能。

### 使用方式

可以通过help命令来获取帮助信息，目前只支持5个命令，对加解密、编码解码和哈希。

![image-20240508010240345](https://picgo-1314385327.cos.ap-guangzhou.myqcloud.com/markdown/2024/05/08/20240508010241.png)

![image-20240508010255452](https://picgo-1314385327.cos.ap-guangzhou.myqcloud.com/markdown/2024/05/08/20240508010256.png)



### native启动速度

单纯对比help命令，native版本能比jar快10倍以上，毕竟jvm启动是需要时间的。

![image-20240507230904869](https://picgo-1314385327.cos.ap-guangzhou.myqcloud.com/markdown/2024/05/07/20240507230906.png)



### native性能

从执行加密命令来看，native比jar的性能要好不少，但hash命令的差距并不明显，这有待深究，不过提升肯定是有的。

> 所以关于native是否能带来更好的性能，能带来多大的提升，要视情况而定，在开发过程中需要自行验证。
>
> 采用native时，最首要的关注是jar启动慢的问题，这在云原生领域很重要；作为个人开发者，我更关注的是应用程序的内存占用，如何节约我的服务器成本，在我实现的一个SpringBoot+Mybatis的简单查询案例中，native的内存占用可以减少30%以上，性能也有一定幅度的提升（至少不会比jar差）。

- encrypt命令，底层使用AES_GCM_256+PBKDF2WithHmacSHA256（密钥派生算法），native有着3.5-4倍的性能提升

![image-20240507233444655](https://picgo-1314385327.cos.ap-guangzhou.myqcloud.com/markdown/2024/05/07/20240507233445.png)

![image-20240508003515709](https://picgo-1314385327.cos.ap-guangzhou.myqcloud.com/markdown/2024/05/08/20240508003517.png)

- hash命令，sha256算法，加盐，native有着30%的性能提升

![image-20240508003852892](https://picgo-1314385327.cos.ap-guangzhou.myqcloud.com/markdown/2024/05/08/20240508003853.png)



### native优化选项

Graalvm编译时有一个选项`-march=native`，会针对CPU指令进行优化，为了解该选项对性能的提升，我进行一次对比，tk未启用该选项，tk-ops启用该选项，从测试结果可以看出，针对不同的指令，该选项带来的性能提升不同，但无论如何都是有提升的！有总比没有好，所以推荐启用（前提是编译平台即目标平台）。

- encrypt命令，底层使用AES_GCM_256+PBKDF2WithHmacSHA256（密钥派生算法），启用选项后有30%-40%的提升

![image-20240508000433736](https://picgo-1314385327.cos.ap-guangzhou.myqcloud.com/markdown/2024/05/08/20240508000434.png)

![image-20240508001431424](https://picgo-1314385327.cos.ap-guangzhou.myqcloud.com/markdown/2024/05/08/20240508001432.png)

- hash命令，sha256算法，加盐，启用选项后的提升可以忽略不计...

![image-20240508001628299](https://picgo-1314385327.cos.ap-guangzhou.myqcloud.com/markdown/2024/05/08/20240508001629.png)

![image-20240508001854419](https://picgo-1314385327.cos.ap-guangzhou.myqcloud.com/markdown/2024/05/08/20240508001855.png)