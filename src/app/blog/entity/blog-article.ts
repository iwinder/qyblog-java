import { User } from "../../system/entity/user";
import { Category } from "../../system/entity/Category";

export class BlogArticle {
    id: number;
    title: string;
    content: string;
    contentHtml: string;
    permaLink: string;
    category: Category;
    author: User;
    thumbnail: string;
    publishedDate: Date;
    isPublished: boolean;
    isDeleted: boolean;
}
