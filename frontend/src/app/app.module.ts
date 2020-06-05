import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { AppRoutingModule } from './app-routing.module';
import { HttpClientModule } from '@angular/common/http';
import { ReactiveFormsModule,FormsModule  } from '@angular/forms';

import { AppComponent } from './app.component';
import { HomeComponent } from './home/home.component';
import { RegisterComponent } from './register/register.component';
import { LoginComponent } from './login/login.component';
import { SubmitSAComponent } from './submit-sa/submit-sa.component';
import { ArticleListComponent } from './article-list/article-list.component';
import { ArticleHtmlComponent } from './article-html/article-html.component';
import { ArticlePdfComponent } from './article-pdf/article-pdf.component';
import { AuthorInfoComponent } from './author-info/author-info.component';
import { KeyWordInfoComponent } from './key-word-info/key-word-info.component';

import { FormReviewComponent } from './form-review/form-review.component';
import { ReviewedPublicationsComponent } from './reviewed-publications/reviewed-publications.component';
import { UnreviewedPublicationsComponent } from './unreviewed-publications/unreviewed-publications.component';
import { PublicationsToReviewComponent } from './publications-to-review/publications-to-review.component';



@NgModule({
  declarations: [
    AppComponent,
    HomeComponent,
    RegisterComponent,
    LoginComponent,
    SubmitSAComponent,
    ArticleListComponent,
    ArticleHtmlComponent,
    ArticlePdfComponent,
    AuthorInfoComponent,
    KeyWordInfoComponent,
    
    FormReviewComponent,
    ReviewedPublicationsComponent,
    UnreviewedPublicationsComponent,
    PublicationsToReviewComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    ReactiveFormsModule,
    FormsModule
    
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
