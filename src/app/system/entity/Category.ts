export class Category {
    id: number;
    title: string;
    key: string;
    keyWord: string;
    parent: Category;
    type: string;
    description: string;
    displayOrder: number;
    hasChildren?: boolean;
    children: Category[];
    expand: boolean;

    getChildren() {
        return this.children;
    }
}
