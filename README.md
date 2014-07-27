Chromecast Gaming Framework
======

Chromecast is a great device for media streaming, but we wanted to take it to the next level and use it to create a social screen. 

Google includes a handfull of streaming media APIs in the Cast framework, but there is little OOTB support for social gaming experiences. 

We took the over 400 lines of boilerplate code that is necessary to communicate with the Chromecast and turned it into a small number of lifecycle calls that developers can use to connect to a cast. 

Usage
=====

As of now, the framework is located inside the example application, but we will be packaging it in a standalone repo soon. Until then look for it in: 

```
package com.davidtschida.android.cast.framework;
```

In your activity's onCreate methos create a new CastManager


```
class MyActivity extends Activity {
	
    @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

        mCastManager = new CastManager(this);

        ...
    }


    @Override
	protected void onResume() {
		super.onResume();
		mCastManager.onResume(this);
	}

	@Override
	protected void onPause() {
		mCastManager.onPause(this);
		super.onPause();
	}

	@Override
	public void onDestroy() {
		mCastManager.onDestroy(this);
		super.onDestroy();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.main, menu);
        mCastManager.setMenu(menu);

		return true;
	}
}
```

You can then send messages in JSON with 

```
mCastManaget.sendMessage(new JSONObject("{\"key\":\"value\""}))
```


Happy casting!
