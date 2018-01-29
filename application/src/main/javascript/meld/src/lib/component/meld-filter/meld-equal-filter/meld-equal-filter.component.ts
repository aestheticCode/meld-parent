import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {Equal, ExpressionVisitor, InSelect, Like, Not, Or, Path, SubQuery} from '../../../common/search/expression.interfaces';
import {HttpClient} from '@angular/common/http';
import {Filter} from '../../../common/search/search.interfaces';
import {Container} from '../../../common/rest/Container';
import {QueryBuilder} from '../../../common/search/search.classes';
import {Selects} from '../../meld-combobox/meld-combobox.interfaces';
import {AndExpression, Expressions} from '../../../common/search/expression.classes';

@Component({
  selector: 'meld-equal-filter',
  templateUrl: 'meld-equal-filter.component.html',
  styleUrls: ['meld-equal-filter.component.css']
})
export class MeldEqualFilterComponent implements OnInit {

  public value : any;

  @Input('equal')
  public equal: Equal;

  public queries: Container<Filter>;

  @Output('filterChange')
  private filterChange: EventEmitter<string> = new EventEmitter<string>();

  onModelChange(event: string) {
    this.filterChange.emit(event);
  }

  constructor(private http: HttpClient) {
  }

  public itemValue = (item) => {
    if (item == null) {
      return null;
    }
    return item;
  };

  schools: Selects<Filter> = (search, callback) => {
    let query = QueryBuilder.query();
    query.index = search.index;
    query.limit = search.limit;
    query.expression = QueryBuilder.and(this.queries.rows.map((filter) => filter.expression));

    Expressions.visit(query.expression, <ExpressionVisitor> {
      visitAnd(expression: AndExpression) {
        expression.expressions.forEach((expression) => Expressions.visit(expression, this));
      },
      visitInSelect(expression: InSelect) {
        Expressions.visit(expression.subQuery, this);
      },
      visitLike(expression: Like) {
        expression.value = search.filter;
      },
      visitNot(expression: Not) {
        Expressions.visit(expression.expression, this);
      },
      visitOr(expression: Or) {
        expression.expressions.forEach((expression) => Expressions.visit(expression, this));
      },
      visitPath(expression: Path) {
        Expressions.visit(expression.expression, this);
      },
      visitSubQuery(expression: SubQuery) {
        Expressions.visit(expression.expression, this);
      }
    });


    this.http.post<Container<any>>(this.queries.links[0].url, query)
      .subscribe((result: Container<any>) => {
        callback(result.rows, result.size);
      });
  };

  ngOnInit(): void {
    let link = this.equal.links[0];

    this.http.get<Container<Filter>>(link.url)
      .subscribe((filter: Container<Filter>) => {
        this.queries = filter;
      });
  }

}
