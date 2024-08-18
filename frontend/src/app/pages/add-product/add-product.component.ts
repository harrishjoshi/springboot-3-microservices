import {Component, inject} from '@angular/core';
import {FormBuilder, FormGroup, ReactiveFormsModule, Validators} from "@angular/forms";
import {NgIf} from "@angular/common";
import {ProductService} from "../../services/product/product.service";
import {Product} from "../../model/product";

@Component({
  selector: 'app-add-product',
  standalone: true,
  imports: [
    ReactiveFormsModule,
    NgIf
  ],
  templateUrl: './add-product.component.html',
  styleUrl: './add-product.component.css'
})

export class AddProductComponent {
  addProductForm: FormGroup;
  private readonly productService = inject(ProductService);
  productCreated: boolean = false;

  constructor(private fb: FormBuilder) {
    this.addProductForm = this.fb.group({
      skuCode: ['', [Validators.required]],
      name: ['', [Validators.required]],
      description: ['', [Validators.required]],
      price: ['', [Validators.required]]
    });
  }

  onSubmit(): void {
    if (this.addProductForm.invalid) {
      alert("Please fill in all the required fields.");
    }

    const product: Product = {
      skuCode: this.addProductForm.get('skuCode')?.value,
      name: this.addProductForm.get('name')?.value,
      description: this.addProductForm.get('description')?.value,
      price: this.addProductForm.get('price')?.value
    }
    this.productService.createProduct(product).subscribe(() => {
      this.productCreated = true;
      this.addProductForm.reset();
    });
  }

  get skuCode() {
    return this.addProductForm.get('skuCode');
  }

  get name() {
    return this.addProductForm.get('name');
  }

  get description() {
    return this.addProductForm.get('description');
  }

  get price() {
    return this.addProductForm.get('price');
  }
}
