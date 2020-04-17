import { Component, OnInit, HostListener, ViewChild, ElementRef, AfterViewInit } from '@angular/core';
import { Router, NavigationEnd } from '@angular/router';
import { ServiceSaService } from '../services/service-sa.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  articles: any;

  @ViewChild('homeBg1', {static : false}) homeBg1: ElementRef;

  @ViewChild('scrollAmount', {static : false}) scrollAmount: ElementRef;

  constructor(private articleService: ServiceSaService, private router: Router) {
    this.router.events.subscribe((val) => {
      if (val instanceof NavigationEnd) {
        this.resetNavbar();
      }
    });

  }

  ngOnInit() {
    this.setNavbar();
    this.articleService.searchByText(' ')
    .subscribe (res => {
      console.log(res);
      this.articles = res;
    });
  }

  ngAfterViewInit() {
    this.render();
  }

  @HostListener('window:scroll', ['$event'])
  onScroll(event) {


    this.setNavbar();
  }

  render() {
    this.homeBg1.nativeElement.style.transform = 'translateY(' + window.pageYOffset * 0.5  + 'px)';
    // this.homeBg1.nativeElement.style.backgroundPositionY = window.pageYOffset * 0.5  + "px";

    this.scrollAmount.nativeElement.style.width = ((window.pageYOffset / window.innerHeight) * 100 > 100 ? 100 : (window.pageYOffset / window.innerHeight) * 100) + '%';

    requestAnimationFrame(this.render.bind(this));
  }

  enter(event) {
    let el = event.target;
    let bg = el.getElementsByClassName('category-background-holder')[0];
    bg.classList.add('category-mouse-enter');
  }

  leave(event) {
    let el = event.target;
    let bg = el.getElementsByClassName('category-background-holder')[0];
    bg.classList.remove('category-mouse-enter');
  }

  resetNavbar() {
    let navbar = document.getElementById('navbar');
    let navLinks = document.getElementsByClassName('nav-link');
    let navBrand = document.getElementsByClassName('navbar-brand')[0];
    let navBarToggler = document.getElementsByClassName('navbar-toggler-icon')[0];

    navbar.classList.add('bg-white');
    navbar.style.boxShadow = '0px 0px 15px 0px rgba(0,0,0,0.9)';
    navBrand.classList.remove('nav-white-color');
    navBrand.classList.add('nav-black-color');
    navBarToggler.classList.add('nav-bar-toggler-icon-black');
    for (let i = 0; i < navLinks.length; i++) {
      navLinks[i].classList.remove('nav-white-color');
      navLinks[i].classList.add('nav-black-color');
    }
  }

  setNavbar() {
    if (window.pageYOffset > 0) {
      this.resetNavbar();
    } else {
      let navbar = document.getElementById('navbar');
      let navLinks = document.getElementsByClassName('nav-link');
      let navBrand = document.getElementsByClassName('navbar-brand')[0];
      let navBarToggler = document.getElementsByClassName('navbar-toggler-icon')[0];
      navbar.classList.remove('bg-white');
      navbar.style.boxShadow = '0px 0px 15px 0px rgba(0,0,0,0.0)';
      navBrand.classList.remove('nav-black-color');
      navBrand.classList.add('nav-white-color');
      navBarToggler.classList.remove('nav-bar-toggler-icon-black');
      for (let i = 0; i < navLinks.length; i++) {
        navLinks[i].classList.remove('nav-black-color');
        navLinks[i].classList.add('nav-white-color');
      }
    }
  }

}
