import {Component, inject, OnInit} from '@angular/core';
import {AsyncPipe, JsonPipe} from "@angular/common";
import {FormsModule} from "@angular/forms";
import {OidcSecurityService} from "angular-auth-oidc-client";
import {Router} from "@angular/router";
import {OrderService} from "../../services/order/order.service";
import {ProductService} from "../../services/product/product.service";
import {Product} from "../../model/product";
import {Order} from "../../model/order";

@Component({
  selector: 'app-homepage',
  templateUrl: './home-page.component.html',
  standalone: true,
  imports: [
    AsyncPipe,
    JsonPipe,
    FormsModule
  ],
  styleUrl: './home-page.component.css'
})

export class HomePageComponent implements OnInit {
  constructor(private oidcSecurityService: OidcSecurityService) {}
  private readonly productService = inject(ProductService);
  private readonly orderService = inject(OrderService);
  private readonly router = inject(Router);
  isAuthenticated: boolean = false;
  products: Array<Product> = [];
  quantityIsNull: boolean = false;
  orderSuccess: boolean = false;
  orderFailed: boolean = false;

  ngOnInit(): void {
    this.oidcSecurityService.isAuthenticated$.subscribe(
      ({isAuthenticated}) => {
        this.isAuthenticated = isAuthenticated;
        this.productService.getProducts()
          .pipe()
          .subscribe(product => {
            this.products = product;
          })
      }
    )
  }

  goToCreateProduct() {
    this.router.navigateByUrl('/add-product').then();
  }

  orderProduct(product: Product, quantity: string) {
    this.oidcSecurityService.userData$.subscribe(result => {
      const userDetails = {
        email: result.userData.email,
        firstName: result.userData.firstName,
        lastName: result.userData.lastName
      }

      if (!quantity) {
        this.orderFailed = true;
        this.quantityIsNull = true;
      } else {
        const order: Order = {
          skuCode: product.skuCode,
          price: product.price,
          quantity: Number(quantity),
          userDetails: userDetails
        }

        this.orderService.orderProduct(order).subscribe(() => {
          this.orderSuccess = true;
        }, error => {
          this.orderFailed = false;
        })
      }
    })
  }
}
