#! /bin/bash
PID=$(cat /www/wwwroot/test.windcoder.com/logs/pid.txt)
kill -9 $PID