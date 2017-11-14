package com.access.accessonandroid.Data;

import android.content.Context;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.CognitoCachingCredentialsProvider;

/**
 * Created by Tyler Collison on 11/10/2017.
 */

public interface ICredentialProvider {

    void CreateProvider(Context context);

    AWSCredentialsProvider GetProvider();

}
