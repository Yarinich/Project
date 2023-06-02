part of 'base_bloc.dart';

@immutable
abstract class BaseState extends Equatable {
  const BaseState();

  @override
  List<Object?> get props => [];
}

abstract class ActionState extends BaseState {}

class InitState extends BaseState {
  const InitState();
}

class ErrorState extends BaseState {
  const ErrorState({
    this.msg,
    this.title,
    this.data,
  });

  final String? msg;
  final String? title;
  final dynamic data;

  @override
  List<Object?> get props => [
        msg,
        title,
        data,
      ];
}

class ProgressState extends BaseState {
  const ProgressState({this.isFullScreen = true});

  final bool isFullScreen;

  @override
  List<Object?> get props => [isFullScreen];
}

class NavigationState extends ActionState {}

class UniqueState extends BaseState {
  @override
  List<Object?> get props => [const Uuid().v4()];
}

class OpenPreviousPageState extends NavigationState {}

class LogoutState extends NavigationState {}

class UserOffNetworkState extends NavigationState {}

class NoNetworkState extends ErrorState {}

class ShimmerState extends ProgressState {
  const ShimmerState() : super(isFullScreen: false);
}
