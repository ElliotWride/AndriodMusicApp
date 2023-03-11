#M inimalist Music
## Introduction
Welcome to Minimalist Music, a bloat free and sleek music player app.
With this app you can
1. Import, view and play all songs that you have downloaded
2. Songs can be played shuffle or in order
3. View album art even if you don't have it downloaded

On the home screen you can play a random song by clicking player when no song is loaded,
go to the song selection screen or go to the player. You can also choose wether to have the
app on shuffle mode or not using the toolbar menu. There is also a link to the github so you can
easily check for updates or new features.

On the song selection screen all songs on the device are imported and are displayed. You can click on 
any song to play it or to view the album art in player.

On the player screen you can pause, play or stop a song as well as view album art.
Album art is fetched from the internet if the album cover is not downloaded. Songs also
retain progress in the case of pausing and then playing.

Background features:
1. Songs continue playing while the app is in the background meaning you can use your phone while playing.
2. When a song finishes a new one is then played for you (randomly or in order depending on
the choice set on the home screen).

#Design rationale
I have designed the app to look very clean so have made very simple UIs.
One of which is the song list screen which uses a recycler view to display the songs. I chose
to use this over list view as it performs better and would use less computational power to
display the songs in the case of the user having a lot of media on their phone.

I Used Explicit intents around the whole project in order to pass data around each activity.
Most notably this was done in the song list activity where on clicking a song the player activity
would be started with the intent of the song to play. This is a really neat way of passing data and 
context to the activity.

The most obvious way to implement Implicit intent was to create an action on a button in the UI
to take the user to the github page. I chose to do this as i intend to keep developing the app
and create optional modules a user can add to the app at their will.

For the option to shuffle or not shuffle i chose to use a menu so i could keep these buttons and
future options away from the main UI as to not clutter it with infrequently used buttons.

I use shared storage to import songs as I prefer to listen to music not using a music streaming service like spotify
as I find adverts and needing a stable internet connection annoying when im just trying to listen to music.
I use Java's inbuilt content resolver to do this as it works very well and is easy to implement.

I chose to make my internet based feature more of an accessory to the app as not to inhibit 
a user's experience of the app if they don't have a connection. For this reason I get the album cover image
from the internet for when the album cover cannot be found on the device. If the album cover is on the device 
then the player sets the background to that instead therefore not relying on the internet to use the app.

For the album cover search i chose to use google's custom search API as it is quite feature rich
and has options for image searching only which is exactly what I needed. While the API is expensive I used a free tier for development.
Being able to only search and retrieve images means that the computation done on the phone is lower and I don't have to retrieve results 
until i find one that is an image. All internet based stuff is done on a separate thread so the user doesn't have to wait for loading
to continue to use the app.

Android has a really good native way of doing media playback using the Media Player object.
This is lightweight and works really well while also having all the features I needed.
I used custom made classes to keep track of songs and playback as this keeps things nice and neat in the code.

## Novel Features
There is an image fetcher that gets images from the internet to display album art
when the user doesn't have it downloaded.
When no song is loaded going to the player will choose a song randomly

## Challenges
I found Android studio hard to use at first as the UI is quite overwhelming and buttons 
don't seem to be where they should be. Also most of the tutorials and examples I found online
were written for old and often deprecated. This was a problem as lots of stuff just outright didn't work 
and as there was very little documentation of alternatives so this slowed down development greatly.

Also i found that android studio would sometime not recognise XML files or the Gradle would break somehow
which often was solved by fiddling with rebuilding, restarting the ide ect.

##Future Improvements
I would like to implement a bunch of quality of life features like being able to control music
in the notification bar or on the lockscreen. Or being able to view and seek how far you are
in a song. I would also like to implement playlists and the ability to view by artist or album.


