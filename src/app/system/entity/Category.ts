export class Category {
    id: number;
    title: string;
    key: string;
    parent: Category;
    type: string;
    displayOrder: number;
    hasChildren?: boolean;
}
