#! /bin/bash
# 检测ip端口，如果失败，重新启动，并发送报警邮件
# THE_URL = "121.36.247.59:8000/api/test/sayHello"
FAIL_COUNT=0
THE_IP_PORT="114.***.**.84"
for ((i=1;i<=3;i++)); do
  if  netstat -tlpn | grep 8000
  then
     echo "$THE_IP_PORT 端口存在"
     break
  else
    echo "端口死亡"
    let FAIL_COUNT++
    source /www/wwwroot/windcoder.com/stop.sh
    echo "重启端口"
    source /www/wwwroot/windcoder.com/start.sh
    if [ $FAIL_COUNT -eq 1 ]; then
      DATE_N=`date "+%Y-%m%d %H:%M:%S"`
      echo "$THE_IP_PORT 端口死亡时间：${DATE_N}" >> /www/wwwroot/windcoder.com/logs/check_ipPort.log #记录死亡日志
    fi
    sleep 20s
  fi
  done
  if [ $FAIL_COUNT -eq 3 ]; then
      echo "Warning: $THE_IP_PORT Access failure! Need Send Email!"  >> /www/wwwroot/windcoder.com/logs/check_ipPort.log #记录死亡日志
  echo "网站$THE_IP_PORT 坏掉，请及时处理" | mail -s "$THE_IP_PORT网站高危，需要重启" 409576584@qq.com
  fi











#! /bin/bash
# 检测ip端口，如果失败，重新启动，并发送报警邮件
# THE_URL = "121.36.247.59:8000/api/test/sayHello"
FAIL_COUNT=0
THE_IP_PORT="114.***.**.84"
for ((i=1;i<=3;i++)); do
  if  netstat -tlpn | grep 8000
  then
     echo "$THE_IP_PORT 端口存在"
     break
  else
    echo "端口死亡"
    let FAIL_COUNT++
    source /www/wwwroot/windcoder.com/stop.sh
    echo "重启端口 star"
    source /www/wwwroot/windcoder.com/start.sh
    echo "重启端口 end"
    if [ $FAIL_COUNT -eq 1 ]; then
      DATE_N=`date "+%Y-%m%d %H:%M:%S"`
      echo "$THE_IP_PORT 端口死亡时间：${DATE_N}" >> /www/wwwroot/windcoder.com/logs/check_ipPort.log #记录死亡日志
    fi
    sleep 60s
  fi
done
if [ $FAIL_COUNT -eq 3 ]; then
    echo "Warning: $THE_IP_PORT Access failure! Need Send Email!"  >> /www/wwwroot/windcoder.com/logs/check_ipPort.log #记录死亡日志
echo "网站$THE_IP_PORT 坏掉，请及时处理" | mail -s "$THE_IP_PORT网站高危，需要重启" 409576584@qq.com
fi



#! /bin/bash
# 检测ip端口，如果失败，重新启动，并发送报警邮件
# THE_URL = "121.36.247.59:8000/api/test/sayHello"
FAIL_COUNT=0
THE_IP_PORT="114.***.**.84"
for ((i=1;i<=3;i++)); do
  if  netstat -tlpn | grep 8000
  then
     echo "$THE_IP_PORT 端口存在"
     break
  else
    echo "端口死亡"
    let FAIL_COUNT++
    source /www/wwwroot/windcoder.com/stop.sh
    echo "重启端口 star"
    source /www/wwwroot/windcoder.com/start.sh
     echo "重启端口 end"
    if [ $FAIL_COUNT -eq 1 ]; then
      DATE_N=`date "+%Y-%m%d %H:%M:%S"`
      echo "$THE_IP_PORT 端口死亡时间：${DATE_N}" >> /www/wwwroot/windcoder.com/logs/check_ipPort.log #记录死亡日志
    fi
    sleep 60s
  fi
  done
  if [ $FAIL_COUNT -eq 3 ]; then
      echo "Warning: $THE_IP_PORT Access failure! Need Send Email!"  >> /www/wwwroot/windcoder.com/logs/check_ipPort.log #记录死亡日志
  echo "网站$THE_IP_PORT 坏掉，请及时处理" | mail -s "$THE_IP_PORT网站高危，需要重启" 409576584@qq.com
  fi