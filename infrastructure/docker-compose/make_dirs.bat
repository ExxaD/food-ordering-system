@ECHO OFF
rmdir volumes /s /q || ECHO Volumes directory doesn't exist. Creating...
mkdir volumes\kafka\broker-1 || (ECHO Error creating directory volumes\kafka\broker-1 & EXIT /B %errorlevel%)
mkdir volumes\kafka\broker-2 || (ECHO Error creating directory volumes\kafka\broker-2 & EXIT /B %errorlevel%)
mkdir volumes\kafka\broker-3 || (ECHO Error creating directory volumes\kafka\broker-3 & EXIT /B %errorlevel%)
mkdir volumes\zookeeper\data || (ECHO Error creating directory volumes\zookeeper\data & EXIT /B %errorlevel%)
mkdir volumes\zookeeper\transactions || (ECHO Error creating directory volumes\zookeeper\transactions & EXIT /B %errorlevel%)

ECHO All folders were created successfully.