import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Product} from "../../model/product";
import {Observable} from "rxjs";
import {PRODUCT_API} from "../../constants";

@Injectable({
  providedIn: 'root'
})

export class ProductService {

  constructor(private http: HttpClient) {
  }

  getProducts(): Observable<Array<Product>> {
    return this.http.get<Array<Product>>(`${PRODUCT_API}`);
  }

  createProduct(product: Product): Observable<Product> {
    return this.http.post<Product>(`${PRODUCT_API}`, product);
  }
}
