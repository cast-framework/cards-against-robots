/*
 * Copyright (C) 2014 Google Inc. All Rights Reserved. 
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at 
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software 
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and 
 * limitations under the License.
 */

package com.davidtschida.android.cards;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;

import com.davidtschida.android.cards.fragments.CzarFragment;
import com.davidtschida.android.cast.framework.CastManager;


/**
 * Main activity to send messages to the receiver.
 */
public class MainActivity extends ActionBarActivity implements CastmanagerHost {

	private static final String TAG = MainActivity.class.getSimpleName();

	private static final int REQUEST_CODE = 1;

    CastManager mCastManager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

        mCastManager = new CastManager(this);

        initActionBar();

        getSupportFragmentManager().beginTransaction().add(R.id.content, CzarFragment.newInstance(new CardItem("This is bullshit")), "CardListFragment").commit();
	}


    private void initActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(
                android.R.color.transparent));
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

    @Override
    public CastManager getCastmanager() {
        return mCastManager;
    }
}
