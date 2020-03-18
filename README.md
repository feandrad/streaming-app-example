● Either fetch and parse video manifest file(located at https://bit.ly/2MIjM4F), or package the file with your app. However if you choose to package this file into the Android project, then the offline playing becomes mandatory - *Manifest still on the app* 
● Display the contents of the manifest in a clickable gallery, where each entry is comprised of a video thumbnail and title - *Done*
● In portrait mode, display a selected video above the gallery, i.e. YouTube in portrait mode - *Missing gallery below the video*
● In landscape mode, videos must play fullscreen - *Still shows statusbar and android controls*
● Video playback position must be maintained through orientation changes - *Done*
● The next video in the list should auto-play after first user selection completes - *TODO*
● Bonus: Videos can play offline(however if you choose to package the json file, then this
feature is mandatory) - *Will not be done, I will fetch and parse the video manifest*
● Bonus: Each gallery entry displays video duration alongside title and thumbnail - *TODO*
● Bonus: Write an instrumentation test for part of the app - *TODO*