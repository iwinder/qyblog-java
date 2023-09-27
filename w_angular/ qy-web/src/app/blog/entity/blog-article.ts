import { Category } from "../../core/entity/Category";
import { User } from "../../core/entity/User";


export class BlogArticle {
    id: number;
    title: string;
    content: string;
    contentHtml: string;
    summary: string;
    permaLink: string;
    category: Category;
    author: User;
    thumbnail: string;
    publishedDate: Date;
    isPublished: boolean;
    isDeleted: boolean;
    tagStrings: string[];
}
