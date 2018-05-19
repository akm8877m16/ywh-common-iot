ywh-common-iot

初步接口测试版本， 实现用户登录以及设备权限接口权限判断
目前测试用户有两个：1 dave 密码secret  2 anil 密码password

要想使用设备遥控接口，首先要登陆获取token

http://118.190.202.155:8082/uaa/oauth/token?grant_type=password&username=dave&password=secret
注意http 请求头中设置

![](https://github.com/akm8877m16/ywh-common-iot/raw/master/pics/登陆获取token.PNG)

目前只是测试阶段，所以token寿命为2分钟， refresh_token寿命为5分钟，实际生产环境会加为12小时和5天

获取token后既可以尝试调用遥控接口

http://118.190.202.155:8082/mqtt/control/868575023510201?access_token=ae3355a5-cd88-49ed-a6ef-4899c600ab38
