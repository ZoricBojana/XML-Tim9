import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { HomeComponent } from './home/home.component';
import { RegisterComponent } from './register/register.component';
import { LoginComponent } from './login/login.component';
import { SubmitSAComponent } from './submit-sa/submit-sa.component';
import { ArticleHtmlComponent } from './article-html/article-html.component';
import { ArticlePdfComponent } from './article-pdf/article-pdf.component';
import { AuthorInfoComponent } from './author-info/author-info.component';
const routes: Routes = [
  {
    path: '',
    redirectTo: '/home',
    pathMatch: 'full'
  },
  {
    path : 'home',
    component : HomeComponent
  },

  {
    path : 'register',
    component : RegisterComponent
  },
  {
    path : 'login',
    component : LoginComponent
  },
  {
    path : 'submitPost',
    component : SubmitSAComponent
  }, {
    path : 'articles/:id',
    component : ArticleHtmlComponent
  }, {
    path : 'articles/pdf/:id',
    component : ArticlePdfComponent
  }, {
    path : 'articles/author/:id',
    component : AuthorInfoComponent
  }

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
