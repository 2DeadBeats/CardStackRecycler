# CardStackRecycler [![](https://jitpack.io/v/com.2deadbeats/cardstackrecycler.svg)](https://jitpack.io/#com.2deadbeats/cardstackrecycler)
---
Simple recycler that behaves like stack of playing cards.


Example
-------

![Sample application](https://cdn.2deadbeats.com/cardstack-sample.gif)

Installation
-----

Add JitPack to your project's ```build.gradle```:
```
allprojects {
	repositories {
		[...]
		maven { url 'https://jitpack.io' }
	}
}
```
then add dependency to your application's module:
```
dependencies {
        implementation 'com.2deadbeats:cardstackrecycler:0.1.0'
}
```
Usage
-----

### Layout
Add CardStackRecycler to your layout:
```xml
<com.twodeadbeats.cardstackrecycler.CardStackRecycler
    android:id="@+id/cardStackRecycler"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    app:centerScrollEnabled="true"
    app:maxCards="3"
    app:paginationEnabled="false"
    tools:layoutManager="com.twodeadbeats.cardstackrecycler.CardStackLayoutManager"
    tools:listitem="@layout/item_card" />
```

there are 3 parameters that you can modify:
* *boolean* ```app:centerScrollEnabled``` - if set to true top card will be automatically centered after user stops moving stack
* *int* ```app:maxCards``` - number of cards visible on stack (including the top card)
* *boolean* ```app:paginationEnabled``` - if set to true user will not be able to scroll more than one card at once

### Feed recycler with cards
Write an adapter class extending ```RecyclerView.Adapter``` just like for standard RecyclerView and then populate it with your data. Check sample project for more details.

License
-------

```
Copyright 2019 2DeadBeats

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
