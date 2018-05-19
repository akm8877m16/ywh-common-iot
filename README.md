ywh-common-iot

初步接口测试版本， 实现用户登录以及设备权限接口权限判断
目前测试用户有两个：1 dave 密码secret  2 anil 密码password

要想使用设备遥控接口，首先要登陆获取token

http://118.190.202.155:8082/uaa/oauth/token?grant_type=password&username=dave&password=secret

![]https://github.com/ywh/ywh-common-iot/raw/master/pics/登陆获取token.PNG
