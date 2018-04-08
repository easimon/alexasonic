#!/usr/bin/env bash

BASEDIR="./volumes/music"

function createmp3() {
  TRACK="$1"
  ARTIST="$2"
  ALBUM="$3"
  SONG="$4"
  COVER="$5"
  GENRE="$6"
  YEAR="$7"
  LENGTH="$8"
  DIRECTORY="$BASEDIR/$ARTIST/$ARTIST - $ALBUM"
  FILENAME="$DIRECTORY/$TRACK - $ARTIST - $SONG"

  mkdir -p "$DIRECTORY"

  sox -r 44100 -c 2 -n "$FILENAME.wav" synth $LENGTH sin %-12 sin %-9 sin %-5 sin %-2
  lame --preset standard "$FILENAME.wav" "$FILENAME.mp3"
  rm "$FILENAME.wav"
  eyeD3 -n "$TRACK" \
    -a "$ARTIST" \
    -A "$ALBUM" \
    -t "$SONG" \
    -G "$GENRE" \
    --release-date "$YEAR" --recording-date "$YEAR" \
    --add-image "$COVER:FRONT_COVER" "$FILENAME.mp3"
}

rm -rf $BASEDIR

createmp3 "01" "Horst Honk" "Honking the Tonk" "Honking Introlude"         "ganesha.jpg" "17" "1995" "120"
createmp3 "02" "Horst Honk" "Honking the Tonk" "Tonky Thong"               "ganesha.jpg" "17" "1995" "99"
createmp3 "03" "Horst Honk" "Honking the Tonk" "Shoo shoo"                 "ganesha.jpg" "17" "1995" "200"
createmp3 "04" "Horst Honk" "Honking the Tonk" "Funky-Tonk"                "ganesha.jpg" "17" "1995" "100"
createmp3 "05" "Horst Honk" "Honking the Tonk" "Tonky Interlude"           "ganesha.jpg" "17" "1995" "60"
createmp3 "06" "Horst Honk" "Honking the Tonk" "I Don't Care About Titles" "ganesha.jpg" "17" "1995" "300"
createmp3 "07" "Horst Honk" "Honking the Tonk" "Song Without A Name"       "ganesha.jpg" "17" "1995" "133"
createmp3 "08" "Horst Honk" "Honking the Tonk" "Same Old Shit"             "ganesha.jpg" "17" "1995" "250"
createmp3 "09" "Horst Honk" "Honking the Tonk" "Where Is My Honky-Tonk"    "ganesha.jpg" "17" "1995" "321"
createmp3 "10" "Horst Honk" "Honking the Tonk" "Still Got The Honk"        "ganesha.jpg" "17" "1995" "100"
createmp3 "11" "Horst Honk" "Honking the Tonk" "Outro"                     "ganesha.jpg" "17" "1995" "90"

createmp3 "01" "Artist Number 5" "Underground" "In The Underground"      "underground.jpg" "16" "2012" "120"
createmp3 "02" "Artist Number 5" "Underground" "Have To Catch The Train" "underground.jpg" "16" "2012" "99"
createmp3 "03" "Artist Number 5" "Underground" "Where's My Ticket"       "underground.jpg" "16" "2012" "200"
createmp3 "04" "Artist Number 5" "Underground" "The Tram is OK As Well"  "underground.jpg" "16" "2012" "100"
createmp3 "05" "Artist Number 5" "Underground" "Final Destination"       "underground.jpg" "16" "2012" "60"
