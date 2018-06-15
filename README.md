ywh-common-iot

初步接口测试版本， 实现用户登录以及设备权限接口权限判断
目前测试用户有两个：1 dave 密码secret  2 anil 密码password

要想使用设备遥控接口，首先要登陆获取token

http://118.190.202.155:8082/uaa/oauth/token?grant_type=password&username=dave&password=secret
注意http 请求头中设置 Authorization：Basic Y2xpZW50OmNsaWVudF9zZWNyZXQ=
![](https://github.com/akm8877m16/ywh-common-iot/raw/master/pics/header_Authorization.PNG)

![](https://github.com/akm8877m16/ywh-common-iot/raw/master/pics/登陆获取token.PNG)

如果用户名密码不正确，则登陆失败

![](https://github.com/akm8877m16/ywh-common-iot/raw/master/pics/登陆失败错误用户名或者密码.PNG)

目前只是测试阶段，所以token寿命为2分钟， refresh_token寿命为5分钟，实际生产环境会加为12小时和5天

如果token失效，需要获取新token, 有两种方式 1 调用获取token接口 2 调用刷新token接口
Post  参数放入body或url均可
http://118.190.202.155:8082/uaa/oauth/token?grant_type=refresh_token&refresh_token=18e0c7bd-bc23-464c-b160-36a3cc43c483&client_id=client&client_secret=client_secret

![](https://github.com/akm8877m16/ywh-common-iot/raw/master/pics/刷新token.PNG)

获取token后既可以尝试调用遥控接口

http://118.190.202.155:8082/mqtt/control/868575023510201?access_token=ae3355a5-cd88-49ed-a6ef-4899c600ab38

如果token已经过期，则返回： 

![](https://github.com/akm8877m16/ywh-common-iot/raw/master/pics/遥控设备超时.PNG)

如果token验证通过，则先检测设备是否与用户绑定，目前测试只绑定用户dave 的一台设备：868575023510201， anil没有绑定设备
所以nail用户无法遥控设备：868575023510201

![](https://github.com/akm8877m16/ywh-common-iot/raw/master/pics/遥控非所属设备.PNG)

只有token验证通过，并且检测设备是否与用户绑定通过，才能调用真正的遥控接口：
http://118.190.202.155:8082/mqtt/control/868575023510201?access_token=1f2b69ad-403e-45a3-b485-6248eda0754e
需要通过post 传递参数： attribute: topic属性， 实际topic为：mqtt根目录(目前默认/device)/设备id(比如868575023510201)/attribute值
                        payload: 遥控指令，目前传递类型为字符串

![](https://github.com/akm8877m16/ywh-common-iot/raw/master/pics/遥控设备成功.PNG)

可以配合mqtt客户端检测指令是否收到

后续功能(还未做)
1 根据attribute获取实时数据接口

2 添加用户接口

3 用户设备绑定接口

4 网关链路跟踪等性能检测服务

5 最后将http转成https

2018/05/28 更新，　用户接口，设备绑定接口已经添加，具体接口使用说明参见http://118.190.202.155:8082/swagger-ui.html
注意需要先获取token才能成功调用接口．

2018/06/15 更新:
                1 网关改用https
                2 增加nNB-IOT 微服务模块
                3 数据库连接池改为druid
                
总结一下目前的监控页面:
1 服务注册中心:http://118.190.202.155:8000/

２ API 接口调试：https://118.190.202.155:8082/swagger-ui.html
3 数据库线程池监控:
      https://118.190.202.155:8082/uaa/druid/weburi.html
      https://118.190.202.155:8082/mqtt/druid/weburi.html
