export class Page<T> {
    first: boolean;
    last: boolean;
    totalPages: number;
    totalElements: number;
    number: number;
    size: number;
    sort: string;
    numberOfElements: number;
    content: T[];
}
