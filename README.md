# Cribbage_Android
An implementation of the game of cribbage for Android.

Based off of the command line version [here](https://github.com/ryanramsdell27/cribbage). Changes to the local version in this project will eventually be pushed back there.

## Project Structure
There is currently only one activity in this project, `MainActivity`. It sets up the ui and runs the command line `cribbage` object in a new thread. Communication between the UI thread and objects are made through calls via the `post` method to add UI events to the event queue. 
The files located in [`java/com/cribbage`](https://github.com/ryanramsdell27/Cribbage_Android/tree/master/app/src/main/java/com/rr/cribbage_android) correspond to the command line version linked above.
The files located in [`java/com/rr/cribbage_android`](https://github.com/ryanramsdell27/Cribbage_Android/tree/master/app/src/main/java/com/cribbage) are associated with the android side of things. Some of the classes implemented here are:
- `UIPlayer` extends the player class from the command line version of the game, and adds some methods necessary for visual updates. All player classes should extend this one or for AI players be passed to the constructor of the `UICPUPlayer`
- `UICPUPlayer` is a wrapper class for the AI players from the command line version
- `UIInteractivePlayer` handles input from the user for the `discard` and `peg` methods
- `PlayingCardView` is a visual representation of a card
- `HandLayout` can be used to show a stack of cards and handles how they are arranged on the screen
