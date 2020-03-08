#!/bin/bash
set -e

echo "{'project_info':{'project_number':'','project_id':''},'client':[{'client_info':{'mobilesdk_app_id':'1:111111111111:android:1111111111111111','android_client_info':{'package_name':'uk.co.cerihughes.mgm.android'}},'api_key':[{'current_key':'1'}]}]}" > app/google-services.json
echo "<resources><string name='google_maps_api_key'>1</string></resources>" > app/src/main/res/values/secrets.xml