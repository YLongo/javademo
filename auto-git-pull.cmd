@echo off

for /d %%i in (f:\project\htrip\*) do ( 

    echo %%i
    cd /d %%i
    chdir
    git pull
    cd ../
)