import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { QyCategoryListComponent } from './category-list/category-list.component';
import { QyCategoryEditComponent } from './category-edit/category-edit.component';
import { CategoryService } from '../../service/category.service';

const routes: Routes = [
    {
        path: '', children: [
            { path: '', component: QyCategoryListComponent },
            { path: ':categoryId/edit', component: QyCategoryEditComponent }
        ]
    },
];

@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule],
    providers: [CategoryService]
})
export class CategoryRoutingModule { }

export const routedComponents = [
    QyCategoryListComponent,
    QyCategoryEditComponent
];
