package de.leanovate.routeegenerator;

import static de.leanovate.routeegenerator.combinators.CharParsers.elem;
import static de.leanovate.routeegenerator.combinators.CharParsers.ignoreCase;
import static de.leanovate.routeegenerator.combinators.CharParsers.keyword;
import static de.leanovate.routeegenerator.combinators.CharParsers.oneOrMoreOf;
import static de.leanovate.routeegenerator.combinators.CharParsers.zeroOrMoreOf;
import static de.leanovate.routeegenerator.combinators.CoreParsers.eof;
import static de.leanovate.routeegenerator.combinators.CoreParsers.firstOf;
import static de.leanovate.routeegenerator.combinators.CoreParsers.oneOrMore;
import static de.leanovate.routeegenerator.combinators.CoreParsers.oneOrMoreSep;
import static de.leanovate.routeegenerator.combinators.CoreParsers.opt;
import static de.leanovate.routeegenerator.combinators.CoreParsers.sequence;
import static de.leanovate.routeegenerator.combinators.CoreParsers.zeroOrMore;
import static de.leanovate.routeegenerator.combinators.CoreParsers.zeroOrMoreSep;

import de.leanovate.routeegenerator.combinators.CharInput;
import de.leanovate.routeegenerator.combinators.CharSequenceInput;
import de.leanovate.routeegenerator.combinators.Parser;
import de.leanovate.routeegenerator.model.ActionParameter;
import de.leanovate.routeegenerator.model.ControllerAction;
import de.leanovate.routeegenerator.model.MethodRoutePattern;
import de.leanovate.routeegenerator.model.PathActionParameter;
import de.leanovate.routeegenerator.model.PathRoutePattern;
import de.leanovate.routeegenerator.model.PrefixRoutePattern;
import de.leanovate.routeegenerator.model.QueryActionParameter;
import de.leanovate.routeegenerator.model.RemainingRoutePattern;
import de.leanovate.routeegenerator.model.RouteRule;
import de.leanovate.routeegenerator.model.SegementRoutePattern;
import de.leanovate.routeegenerator.model.ValueActionParameter;

import java.util.List;
import java.util.stream.Collectors;

public class RouteParser {
    public Parser<CharInput, RouteRules> routePatterns() {

        final RouteRules result = new RouteRules();
        return sequence(zeroOrMore(
                        line(result)
                                  ),
                eof()).map((any) -> result);
    }

    public Parser<CharInput, RouteRules> line(final RouteRules routePatterns) {

        return sequence(
                firstOf(
                        comment(),
                        packageDecl().map((p) -> {
                            routePatterns.packageName = p;
                            return null;
                        }),
                        routeRule().map((r) -> {
                            routePatterns.addRule(r);
                            return null;
                        }),
                        whitespace().map((any) -> null)
                       ),
                whitespace(),
                lineBreak()
                       ).map((any) -> routePatterns);
    }

    public Parser<CharInput, ?> comment() {

        return sequence(elem('#'), zeroOrMoreOf((ch) -> ch != '\n'));
    }

    public Parser<CharInput, String> packageDecl() {

        return sequence(
                whitespace(),
                keyword("package"),
                whitespaceDelim(),
                oneOrMoreSep(javaIdent(), delim('.'))
                       ).map((t) -> t._4.stream().collect(Collectors.joining(".")));
    }

    public Parser<CharInput, RouteRule> routeRule() {

        return sequence(
                whitespace(),
                method(),
                whitespaceDelim(),
                pathPattern(),
                whitespaceDelim(),
                controllerAction()
                       ).map((t) -> new RouteRule(t._4, new MethodRoutePattern(t._2, t._6)));
    }

    public Parser<CharInput, String> method() {

        return firstOf(
                ignoreCase("GET"),
                ignoreCase("POST"),
                ignoreCase("PUT"),
                ignoreCase("DELETE"),
                ignoreCase("OPTION"),
                ignoreCase("HEAD"),
                ignoreCase("TRACE"),
                ignoreCase("PATCH"),
                ignoreCase("CONNECT")
                      );
    }

    public Parser<CharInput, List<PathRoutePattern>> pathPattern() {

        return oneOrMore(
                firstOf(pathSegment(), pathRemain(), pathPrefix()));
    }

    public Parser<CharInput, PathRoutePattern> pathPrefix() {

        return sequence(
                elem('/'),
                oneOrMoreOf(
                        (ch) -> (ch >= 'a' && ch <= 'z') || (ch >= 'A' && ch <= 'Z') || (ch >= '0' && ch <= '9')
                                || ch == '_' || ch == '-' || ch == '.' || ch == '*' || ch == '%',
                        (ch) -> "expected uri path")
                       ).map((t) -> new PrefixRoutePattern(t._1 + t._2));
    }

    public Parser<CharInput, PathRoutePattern> pathSegment() {

        return sequence(
                elem('/'),
                elem(':'),
                javaIdent()
                       ).map((t) -> new SegementRoutePattern(t._1 + t._2 + t._3));
    }

    public Parser<CharInput, PathRoutePattern> pathRemain() {

        return sequence(
                elem('/'),
                elem('*'),
                javaIdent()
                       ).map((t) -> new RemainingRoutePattern(t._1 + t._2 + t._3));
    }

    public Parser<CharInput, ControllerAction> controllerAction() {

        return sequence(
                opt(elem('@')),
                controllerClass(),
                controllerMethod(),
                actionParameters()
                       ).map((t) -> new ControllerAction(t._2, t._3, t._1.isPresent(), t._4));
    }

    public Parser<CharInput, String> controllerClass() {

        return oneOrMore(
                sequence(javaIdent(), delim('.')).map((t) -> t._1)
                        ).map((n) -> n.stream().collect(Collectors.joining(".")));
    }

    public Parser<CharInput, String> controllerMethod() {

        return javaIdent();
    }

    public Parser<CharInput, List<ActionParameter>> actionParameters() {

        return sequence(
                delim('('),
                zeroOrMoreSep(actionParameter(), delim(',')),
                delim(')')
                       ).map((t) -> t._2);
    }

    public Parser<CharInput, ActionParameter> actionParameter() {

        return firstOf(
                valueActionParameter(),
                queryActionParameter(),
                pathActionParameter()
                      );
    }

    public Parser<CharInput, ActionParameter> valueActionParameter() {

        return firstOf(numberLiteral(), stringLiteral()).map(ValueActionParameter::new);
    }

    public Parser<CharInput, ActionParameter> pathActionParameter() {

        return javaIdent().map(PathActionParameter::new);
    }

    public Parser<CharInput, ActionParameter> queryActionParameter() {

        return sequence(
                javaIdent(),
                delim('?'),
                opt(
                        sequence(
                                delim('='),
                                firstOf(numberLiteral(), stringLiteral())
                                ).map((t) -> t._2)
                   )
                       ).map((t) -> new QueryActionParameter(t._1, t._3));
    }

    public Parser<CharInput, String> javaIdent() {

        return sequence(
                elem("javaIdentifierStart", Character::isJavaIdentifierStart),
                zeroOrMoreOf(Character::isJavaIdentifierPart)
                       ).map((t) -> t._1 + t._2);
    }

    public Parser<CharInput, String> lineBreak() {

        return oneOrMoreOf("\r\n");
    }

    public Parser<CharInput, ?> delim(char delimChar) {

        return sequence(
                whitespace(),
                elem(delimChar),
                whitespace()
                       );
    }

    public Parser<CharInput, String> whitespaceDelim() {

        return zeroOrMoreOf(" \t");
    }

    public Parser<CharInput, String> whitespace() {

        return zeroOrMoreOf(" \t");
    }

    public Parser<CharInput, String> numberLiteral() {

        return oneOrMoreOf((ch) -> Character.isDigit(ch) || ch == '.',
                (ch) -> "expected number but " + ch + " found");
    }

    public Parser<CharInput, String> stringLiteral() {

        return sequence(
                elem('"'),
                zeroOrMoreOf((ch) -> ch != '"'),
                elem('"')
                       ).map((t) -> t._1 + t._2 + t._3);
    }

    public static RouteRules parse(final CharSequence in) {

        final RouteParser parser = new RouteParser();

        return parser.routePatterns().apply(new CharSequenceInput(in)).get();
    }
}