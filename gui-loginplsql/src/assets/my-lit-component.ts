import { LitElement, html, css } from 'lit-element';

export class MyLitComponent extends LitElement {
  static override styles = css`
    :host {
      display: block;
      padding: 16px;
      border: 1px solid #ccc;
    }
  `;

  override render() {
    return html`
      <p>This is my Lit component!</p>
    `;
  }
}

customElements.define('my-lit-component', MyLitComponent);
