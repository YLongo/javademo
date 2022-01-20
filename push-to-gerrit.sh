#!/bin/bash

branchName=`git symbolic-ref --short -q HEAD` ##获取分支名
echo curent branchName: $branchName
git push origin head:refs/for/$branchName

read -p "press any key to stop" -n 1