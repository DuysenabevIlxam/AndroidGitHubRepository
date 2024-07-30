package com.example.furniq.di

import com.example.furniq.api.ApiService
import com.example.furniq.repo.auth_repo.AuthRepo
import com.example.furniq.settings.Settings
import com.example.furniq.ui.auth.getProfil.ProfileVM
import com.example.furniq.ui.auth.sign_in.SignInVM
import com.example.furniq.ui.auth.sign_up.SignUpVM
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val networkModule = module {
    single {
        GsonBuilder().setLenient().create()
    }

    single {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .retryOnConnectionFailure(true)
            .build()
    }

    single {
        Retrofit.Builder()
            .baseUrl("https://api.furniq.uz/api/v1/customer/")
            .addConverterFactory(GsonConverterFactory.create(get()))
            .client(get())
            .build()
    }

    single {
        get<Retrofit>().create(ApiService::class.java)
    }
}


val repositoryModule = module {
    single { AuthRepo(get(), get()) }

}
val viewModule = module {
    viewModel { SignUpVM(get(),get()) }
    viewModel { ProfileVM(get()) }
    viewModel { SignInVM(get(),get()) }


}

val helperModule = module {
    single { Settings(androidApplication().applicationContext) }
}