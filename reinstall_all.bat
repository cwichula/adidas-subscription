@echo off

cd kubernetes/database/
call reinstall_mysql.bat

cd ../kafka/
call reinstall_kafka.bat

cd ../../
call reinstall_subscription.bat

cd ../adidas-subscription-challange/
call reinstall_mail.bat

pause