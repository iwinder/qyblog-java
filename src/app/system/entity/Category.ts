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
    idPath: string;
    isLeaf: boolean = !this.hasChildren;
    // getChildren() {
    //     return this.children;
    // }
}
