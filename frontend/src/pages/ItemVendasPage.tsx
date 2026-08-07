import { DataTable, ApiForm, PageBreadcrumb } from "../components";
import { Plus, X } from "lucide-react";
import { useState } from "react";
import { createPortal } from "react-dom";
import { itensvendaService } from "../services/itensvenda.service";
import { vendasService } from "../services/vendas.service";
import { produtosService } from "../services/produtos.service";

export default function ItemVendasPage() {
  const [createOpen, setCreateOpen] = useState(false);
  const [reloadToken, setReloadToken] = useState(0);
  const closeCreateDialog = () => setCreateOpen(false);


  return (
    <div className="page">

      <PageBreadcrumb items={[{ label: `Listagem de ItemVendas` }]} />

      <header className="page-header">
        <div>
          <h1 className="page-title">{`Listagem de ItemVendas`}</h1>
        </div>
      </header>
      <p style={{ margin: "-18px 0 20px 0" }}>{`Gerencie itemvendas cadastrados, revise status e execute ações recorrentes.`}</p>
      <div className="page-actions">
        <button className="btn btn-primary" type="button" onClick={() => setCreateOpen(true)}>
          <Plus size={16} aria-hidden="true" />
          Adicionar
        </button>
      </div>
      <DataTable load={itensvendaService.list} reloadToken={reloadToken} columns={[{"key":"id","label":"id"},{"key":"vendaId","label":"vendaId"},{"key":"produtoId","label":"produtoId"},{"key":"quantidade","label":"quantidade"},{"key":"preco_unitario","label":"preco_unitario"},{"key":"subtotal","label":"subtotal"}]} idKey={"id"} fields={[{"name":"vendaId","type":"number","required":true,"readOnly":false,"relation":{"endpoint":"/vendas","valueKey":"id","labelKey":"id"}},{"name":"produtoId","type":"number","required":true,"readOnly":false,"relation":{"endpoint":"/produtos","valueKey":"id","labelKey":"nome"}},{"name":"quantidade","type":"number","required":true,"readOnly":false},{"name":"preco_unitario","type":"number","required":true,"readOnly":false},{"name":"subtotal","type":"number","required":true,"readOnly":false}]} relationLoaders={{ "vendaId": vendasService.list, "produtoId": produtosService.list }} onUpdate={itensvendaService.update} onDelete={itensvendaService.remove} />
      {createOpen && createPortal(
        <div className="dialog-overlay" role="presentation" onClick={closeCreateDialog}>
          <section
            className="dialog-content shadcn-dialog"
            role="dialog"
            aria-modal="true"
            aria-labelledby="create-dialog-title"
            onClick={(event) => event.stopPropagation()}
          >
            <header className="dialog-header">
              <div>
                <h2 id="create-dialog-title" className="dialog-title">Adicionar</h2>
                <p className="dialog-description">Preencha os dados para criar um novo registro.</p>
              </div>
              <button className="icon-btn" type="button" onClick={closeCreateDialog} aria-label="Fechar">
                <X size={16} aria-hidden="true" />
              </button>
            </header>
            <ApiForm
              submit={itensvendaService.create}
              fields={[{"name":"vendaId","type":"number","required":true,"readOnly":false,"relation":{"endpoint":"/vendas","valueKey":"id","labelKey":"id"}},{"name":"produtoId","type":"number","required":true,"readOnly":false,"relation":{"endpoint":"/produtos","valueKey":"id","labelKey":"nome"}},{"name":"quantidade","type":"number","required":true,"readOnly":false},{"name":"preco_unitario","type":"number","required":true,"readOnly":false},{"name":"subtotal","type":"number","required":true,"readOnly":false}]}
              submitLabel="Adicionar"
              relationLoaders={{ "vendaId": vendasService.list, "produtoId": produtosService.list }}
              onSuccess={() => {
                setCreateOpen(false);
                setReloadToken((value) => value + 1);
              }}
            />
          </section>
        </div>,
        document.body
      )}
    </div>
  );
}
