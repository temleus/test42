How to 

Project is dependent on 3 libraries:
1. ActionBar Sherlock 
https://github.com/JakeWharton/ActionBarSherlock.git
2. ViewPagerIndicator 
https://github.com/JakeWharton/Android-ViewPagerIndicator.git
3. Facebook SDK 
https://developers.facebook.com/resources/facebook-android-sdk-3.0.1.zip

Download and extract the SDK ZIP file. 
The resulting folder, facebook-android-sdk-x.x.x, contains the SDK itself, samples and others.

All three libraries must be added as library modules.


Project contains inner module named 'test' that is dependent on outer module (scope - test) 

and on test libraries:
robolectric-x.x.jar and junit-x.x.jar located at test/libs directory

test/src directory must be marked as 'test sources'


Building SDK must be 4.0 or later (it doesn't mean that app won't be compatible with 2.x devices)
