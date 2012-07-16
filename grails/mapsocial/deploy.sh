#!/bin/bash

echo -n "Cleaning..."
grails clean > target/build.log 2>&1
echo "done."
echo -n "Building..."
grails war > target/build.log 2>&1
if [ "$?" != "0" ]; then
	echo "failed, exiting (check target/build.log)."
	exit 1;
fi
echo "done."
echo -n "Uploading..."
s3cmd put --acl-public --guess-mime-type target/mapsocial-0.1.war s3://s3-mappu/mapsocial-0.1.war
echo "done."
