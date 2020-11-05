#! /bin/bash
#注意：必须有&让其后台执行，否则没有pid生成   jar包路径为绝对路径
# java -jar /web/share-book.jar > /web/log.txt &
 nohup java -jar qycms.war > /www/wwwroot/test.windcoder.com/logs/qycms.log 2>&1 &

# 将jar包启动对应的pid写入文件中，为停止时提供pid
echo $! > /www/wwwroot/test.windcoder.com/logs/pid.txt