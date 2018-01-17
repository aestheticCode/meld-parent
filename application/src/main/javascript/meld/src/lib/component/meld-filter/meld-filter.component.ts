import {Component, EventEmitter, Input, OnInit, Output, ViewEncapsulation} from '@angular/core';
import {
  Equal, ExpressionVisitor, In, InSelect, IsNull, Levensthein, Like, Not, Or, Path, RestExpression,
  SubQuery
} from '../../common/search/expression.interfaces';
import {AndExpression, Expressions} from '../../common/search/expression.classes';
import {Filter} from '../../common/search/search.interfaces';
import {Strings} from '../../common/utils/Strings';
import {Objects} from '../../common/utils/Objects';

@Component({
  selector: 'meld-filter',
  templateUrl: 'meld-filter.component.html',
  styleUrls: ['meld-filter.component.css'],
  encapsulation: ViewEncapsulation.None
})
export class MeldFilterComponent implements OnInit {

  @Input('filter')
  public filter: Filter;

  public expressions : RestExpression[] = [];

  @Output("filterChange")
  private filterChange : EventEmitter<string> = new EventEmitter<string>();

  onFilterChange(event : string) {
    this.filter.active = Strings.isNotEmpty(event);
    this.filterChange.emit(event);
  }

  ngOnInit() {

    let component = this;

    Expressions.visit(this.filter.expression, <ExpressionVisitor>{
      visitAnd(expression: AndExpression) {
        expression.expressions.forEach((expression) => Expressions.visit(expression, this));
      },
      visitEqual(expression: Equal) {
        component.expressions.push(expression);
      },
      visitIn(expression: In) {
        component.expressions.push(expression);
      },
      visitInSelect(expression: InSelect) {
        Expressions.visit(expression.subQuery, this);
      },
      visitLevensthein(expression: Levensthein) {
        component.expressions.push(expression);
      },
      visitLike(expression: Like) {
        component.expressions.push(expression);
      },
      visitNot(expression: Not) {
        Expressions.visit(expression.expression, this);
      },
      visitNull(expression: IsNull) {
        // No Op
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

  }

}
