package com.example.masterdetailflowkotlintest

import com.example.masterdetailflowkotlintest.modules.Retrofit
import dagger.Module
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import okhttp3.HttpUrl

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [Retrofit::class]
)
class MockNetworkModule : Retrofit() {

    override fun baseUrl(): HttpUrl {
        return MockServer.server.url("http://localhost/")
    }
}