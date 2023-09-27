import { QyConsolePage } from './app.po';

describe('qy-console App', () => {
  let page: QyConsolePage;

  beforeEach(() => {
    page = new QyConsolePage();
  });

  it('should display welcome message', () => {
    page.navigateTo();
    expect(page.getParagraphText()).toEqual('Welcome to app!!');
  });
});
