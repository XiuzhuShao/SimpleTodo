# Project 1 - SimpleTodo

SimpleTodo is an android app that allows building a todo list and basic todo items management functionality including adding new items, editing and deleting an existing item.

Submitted by: Xiuzhu Shao

Time spent: 4 hours spent in total

## User Stories

The following **required** functionality is completed:

* [X] User can **view a list of todo items**
* [X] User can **successfully add and remove items** from the todo list
* [X] User's **list of items persisted** upon modification and and retrieved properly on app restart

The following **optional** features are implemented:

* [X] User can **tap a todo item in the list and bring up an edit screen for the todo item** and then have any changes to the text reflected in the todo list

The following **additional** features are implemented:

* [X] A supportActionBar with a title has been added to both the Main Task and Edit Task screen. Additionally, the Edit Task action bar provides a back arrow
so that the user can return to the main task if they decide not to edit the list item.

## Video Walkthrough

Here's a walkthrough of implemented user stories:

<img src='https://imgur.com/a/r4THyNM' title='Video Walkthrough' width='' alt='Video Walkthrough' />

GIF created with [LiceCap](http://www.cockos.com/licecap/).

## Notes

Describe any challenges encountered while building the app.
A big challenge was implementing the edit task screen. There were two main hurdles:
1) The values received in the onActivityResult override function in MainActivity.kt were of type int? and type string? respectively. These could not be readily used
to set the value within listOfTasks. The resolution was to set a default value for each with an Elvis operator.
2) Originally, if the user clicked on an item in the list by accident and was taken to the Edit screen, there was no way back other than to hit the finish edit button.
The resolution was to look up external documentation on the supportActionBar feature and implement a back arrow feature in the app.

## License

    Copyright 2022 Xiuzhu Shao
    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
