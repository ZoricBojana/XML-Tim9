import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { HomeComponent } from './home/home.component';
import { RegisterComponent } from './register/register.component';
import { LoginComponent } from './login/login.component';
import { SubmitSAComponent } from './submit-sa/submit-sa.component';
import { ArticleHtmlComponent } from './article-html/article-html.component';
import { ArticlePdfComponent } from './article-pdf/article-pdf.component';
import { AuthorInfoComponent } from './author-info/author-info.component';
import { KeyWordInfoComponent } from './key-word-info/key-word-info.component';
import { PublicationsToReviewComponent } from './publications-to-review/publications-to-review.component';

import { ReviewedPublicationsComponent } from './reviewed-publications/reviewed-publications.component';
import { UnreviewedPublicationsComponent } from './unreviewed-publications/unreviewed-publications.component';
import { FormReviewComponent } from './form-review/form-review.component';
import { ArticleTableComponent } from './article-table/article-table.component';
import { UserArticlesComponent } from './user-articles/user-articles.component';
import { ScArticlesComponent } from './sc-articles/sc-articles.component';
import { SubmitReviewComponent } from './submit-review/submit-review.component';
import { RoleGuard } from './guards/role.service';
import { ReviewerForReviewComponent } from './reviewer-for-review/reviewer-for-review.component';
import { CoverLetterComponent } from './cover-letter/cover-letter.component';
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
  }, {
    path : 'articles/keyword/:kw',
    component : KeyWordInfoComponent
  },
  {path : 'unreviewedPub' , component : UnreviewedPublicationsComponent},
  { path : 'reviewedPub', component : ReviewedPublicationsComponent},
  { path : 'pubToReview', component : PublicationsToReviewComponent},

  {
    path : 'formRew',
    component : FormReviewComponent
  },
  {
    path: 'myPapers',
    component: UserArticlesComponent
  },

  {
    path : 'addSc',
    component : ScArticlesComponent
  },
  {
    path : 'submitReview/:id',
    component : SubmitReviewComponent
  },
  {
    path : 'submitCL/:id',
    component : CoverLetterComponent
  },
  {
    path : 'reviewer',
    component : ReviewerForReviewComponent
  }

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
