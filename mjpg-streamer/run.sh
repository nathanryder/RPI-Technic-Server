./mjpg_streamer -i "./input_uvc.so -d /dev/video0 -y  -r 500x400 -f 15" -o "./output_http.so -p 8080 -w ./www"
