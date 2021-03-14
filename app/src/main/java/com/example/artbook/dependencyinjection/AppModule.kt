package com.example.artbook.dependencyinjection

import android.content.Context
import androidx.room.Room
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.artbook.R
import com.example.artbook.api.RetrofitApi
import com.example.artbook.repo.ArtRepository
import com.example.artbook.repo.ArtRepositoryInterface
import com.example.artbook.roomdb.ArtDao
import com.example.artbook.roomdb.ArtDatabase
import com.example.artbook.util.Util.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton
import dagger.hilt.android.components.ApplicationComponent


@Module
@InstallIn(ApplicationComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun injectRoomDatabase(
            @ApplicationContext context: Context
    ) = Room.databaseBuilder(context, ArtDatabase::class.java,"ArtBookDB").build()

    @Singleton
    @Provides
    fun injectDao(
            database: ArtDatabase
    ) = database.artDao()


    @Singleton
    @Provides
    fun injectRetrofitAPI() : RetrofitApi = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL).build().create(RetrofitApi::class.java)


    @Singleton
    @Provides
    fun injectNormalRepo(dao : ArtDao, api: RetrofitApi) = ArtRepository(dao,api) as ArtRepositoryInterface

    @Singleton
    @Provides
    fun injectGlide(@ApplicationContext context: Context) = Glide
            .with(context).setDefaultRequestOptions(
                    RequestOptions().placeholder(R.drawable.ic_launcher_foreground)
                            .error(R.drawable.ic_launcher_foreground)
            )

}